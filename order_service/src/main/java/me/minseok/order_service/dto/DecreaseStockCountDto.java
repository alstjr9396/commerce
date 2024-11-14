package me.minseok.order_service.dto;

public class DecreaseStockCountDto {

    private Long decreaseCount;

    public Long getCount() {
        return decreaseCount;
    }

    public void setDecreaseCount(Long decreaseCount) {
        this.decreaseCount = decreaseCount;
    }
}
