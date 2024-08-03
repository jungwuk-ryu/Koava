package me.jungwuk.koava.models;

public class BasisAssetCodeAndStockName {
    private final String basisAssetCode;
    private final String stockName;

    public BasisAssetCodeAndStockName(String basisAssetCode, String stockName) {
        this.basisAssetCode = basisAssetCode;
        this.stockName = stockName;
    }

    public String getBasisAssetCode() {
        return basisAssetCode;
    }

    public String getStockName() {
        return stockName;
    }
}
