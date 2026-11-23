package com.bijlipay.model.dto;

import com.bijlipay.model.CounterManagement;

import java.util.List;

public class CounterDto {
    private long id;
    private String counter;
    private List<CounterManagement> counterManagements;

    public CounterDto(long id, List<CounterManagement> counterManagements) {
        this.id = id;
        this.counter = "counter"+id;
        this.counterManagements = counterManagements;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public List<CounterManagement> getCounterManagements() {
        return counterManagements;
    }

    public void setCounterManagements(List<CounterManagement> counterManagements) {
        this.counterManagements = counterManagements;
    }

    @Override
    public String toString() {
        return "CounterDto{" +
                "id=" + id +
                ", counter='" + counter + '\'' +
                ", counterManagements=" + counterManagements +
                '}';
    }
}
