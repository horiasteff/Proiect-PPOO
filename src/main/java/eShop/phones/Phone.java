package eShop.phones;

import eShop.enums.Color;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class Phone {

    protected String id;
    protected int index;
    protected String name;
    protected float size;
    protected Integer year;
    protected Color color;
    protected long basePrice;
    protected boolean isBought = false;

    public Phone(int index, String name, float size, Integer year, Color color, long basePrice) {
        this.id = UUID.randomUUID().toString();
        this.index = index;
        this.name = name;
        this.size = size;
        this.year = year;
        this.color = color;
        this.basePrice = basePrice;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public long getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(long basePrice) {
        this.basePrice = basePrice;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }

    @Override
    public String toString() {
         return StringUtils.center(String.valueOf(index), 10, " ") +
                StringUtils.center(name, 14, " ") +
                StringUtils.center(String.valueOf(size), 16, " ") +
                StringUtils.center(String.valueOf(year), 14, " ") +
                StringUtils.center(String.valueOf(color), 10, " ") +
                StringUtils.center(String.valueOf(basePrice), 8, ' ');
    }
}
