package dev.byli.orderstatus.v1.service.client;

import dev.byli.commons.Client;
import dev.byli.commons.Order;
import dev.byli.orderstatus.utils.DigitalSignature;
import dev.byli.orderstatus.v1.dto.BinanceOrderStatusResponse;
import dev.byli.orderstatus.v1.service.adapter.OrderStatusAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;


@Slf4j
@Component
public class BinanceOrderStatusClient implements OrderStatusClient<BinanceOrderStatusResponse> {

    @Value("${order.status.binance.host}")
    private String binanceApiHost;

    @Value("${order.status.binance.sec.key}")
    private String secret;

    @Value("${order.status.binance.api.key}")
    private String apiKey;

    @Autowired
    private DigitalSignature signature;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private List<OrderStatusAdapter> adapters;

    @Override
    public Client getClientType() {
        return Client.BINANCE;
    }

    @Override
    public BinanceOrderStatusResponse getOrderStatus(Order order) {
        LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap();

        headers.add("X-MBX-APIKEY", apiKey);
        String query =String.format("symbol=%s&orderId=%s&timestamp=%d",
                String.format("%s%s",order.getTickerPair().getTicker().getName(),order.getTickerPair().getTickerPair().getName()),
                order.getExternalId(),
                new Date().getTime());
        String signature = this.signature.getSignature(query, this.secret);
        String urlPath = String.format("/api/v3/order?%s&signature=%s", query, signature);

        HttpEntity<String> request = new HttpEntity(null, headers);

        return this.restTemplate.exchange(String.format("%s%s", binanceApiHost, urlPath),
                HttpMethod.GET,request, BinanceOrderStatusResponse.class).getBody();
    }
}
