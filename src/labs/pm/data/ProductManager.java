/*
 * Copyright (c) $originalComment.match ("Copyright \(cl) (\dt)", 1,"-") 24/04/2025 14:48. year.
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
import java.time.LocalDate;

/**
 * @author macbook
 **/
public class ProductManager {

    private Product product;
    private Review review;

    public Product createProduct (int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore) {
        product = new Food(id, name, price, rating, bestBefore);
        return product;
    }

    public Product createProduct (int id, String name, BigDecimal price, Rating rating){
        product = new Drink(id, name, price, rating);
        return product;
    }

    public Product reviewProduct (Product product, Rating rating, String comments) {
        review = new Review(rating, comments);
        this.product = product.applyRating(rating);
        return this.product;
    }

    public void printProductReport (){
        StringBuilder txt = new StringBuilder ();
        txt.append (product);
        txt.append ('\n');
        if (review != null){
            txt.append (review);
        }
        else{
            txt.append ("Not reviewed") ;
        }
        txt.append ('\n');
        System.out.println (txt);
    }
}
