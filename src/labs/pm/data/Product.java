/*
 * Copyright (c) $originalComment.match ("Copyright \(cl) (\dt)", 1,"-") 15/02/2025 15:47. year.
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY OF FITNESS FOR A PARTICULAR PURPOSE.
 * See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public
 * License along with this program. If not,
 * If not, see
 * <http: //www.gnu.org/licenses/>.
 */

package labs.pm.data;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

import static java.math.RoundingMode.HALF_UP;

/**
 * @author macbook
 **/


public abstract class Product {
    private final int id;
    private final String name;
    private final BigDecimal price;
    private final Rating rating;

    @Override
    public String toString() {
        return id+", "+name+", "+price+", "+getDiscount () +", "+rating.getStars()+", "+getBestBefore ();
    }


    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o instanceof Product product) {
            return id == product.id && Objects.equals(name, product.name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Product(){
        this.id = 0;
        this.name = "no name";
        this.price = BigDecimal.valueOf(0);
        this.rating = Rating.NOT_RATED;
    }

    public Product(int id, String name, BigDecimal price, Rating rating) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.rating = rating;
    }

    public Product(int id, String name, BigDecimal price){
        this(id, name, price, Rating.NOT_RATED);
    }

    public abstract Product applyRating(Rating new_rating);

    public LocalDate getBestBefore() {
        return LocalDate.now();
    }

    public static final BigDecimal DISCOUNT_RATE=BigDecimal.valueOf (0.1);

    public Rating getRating() {
        return rating;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public BigDecimal getPrice() {
        return price;
    }


    public BigDecimal getDiscount(){
        return price.multiply(DISCOUNT_RATE).setScale(2, RoundingMode.HALF_UP);
    }


}
