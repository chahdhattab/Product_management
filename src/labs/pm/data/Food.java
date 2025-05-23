/*
 * Copyright (c) $originalComment.match ("Copyright \(cl) (\dt)", 1,"-") 24/04/2025 13:23. year.
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

public final class Food extends Product{

    private LocalDate bestBefore;


    Food(int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore) {
        super(id, name, price, rating);
        this.bestBefore = bestBefore;
    }

    public LocalDate getBestBefore() {
        return LocalDate.now();
    }

    @Override
    public String toString() {
        return super.toString()+", " + bestBefore;
    }

    @Override
    public BigDecimal getDiscount() {
        return (bestBefore.isEqual (LocalDate.now () )) ? super.getDiscount () : BigDecimal.ZERO;
    }

    @Override
    public Product applyRating (Rating newRating){
        return new Food (getId (), getName (), getPrice () ,
                newRating, bestBefore);}
}
