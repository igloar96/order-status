package dev.byli.orderstatus.v1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BinanceOrderStatusResponse {
    private String symbol;//: "BTCUSDT",
    private Long orderId;//: 28,
    private Integer orderListId;//: -1, //Unless OCO, value will be -1
    private String clientOrderId;//:"6gCrw2kRUAF9CvJDGP16IP",
    private Long transactTime;//1507725176595,
    private Float price;//:"0.00000000",
    private String origQty;//:"10.00000000",
    private String executedQty;//:"10.00000000",
    private String cummulativeQuoteQty;//:"10.00000000",
    private String status;//:"FILLED",
    private String timeInForce;//:"GTC",
    private String type;//:"MARKET",
    private String side;//:"SELL"

}
