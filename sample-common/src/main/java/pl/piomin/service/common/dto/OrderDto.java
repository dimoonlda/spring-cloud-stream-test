package pl.piomin.service.common.dto;

public class OrderDto {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public OrderDto setId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id='" + id + '\'' +
                '}';
    }
}
