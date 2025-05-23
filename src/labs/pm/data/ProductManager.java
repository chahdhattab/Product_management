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
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

/**
 * @author macbook
 **/
public class ProductManager {
    private Map<Product , List<Review>> products = new HashMap<>();

    public ProductManager(Locale locale) {
        this (locale.toLanguageTag ());
    }

    public Product createProduct (int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore) {
        Product product = new Food(id, name, price, rating, bestBefore);
        products.putIfAbsent(product, new ArrayList<>());
        return product;
    }

    public Product createProduct (int id, String name, BigDecimal price, Rating rating){
        Product product = new Drink(id, name, price, rating);
        products.putIfAbsent(product, new ArrayList<>());
        return product;
    }

    public Product reviewProduct (int id, Rating rating, String comments) {
        return reviewProduct (findProduct (id), rating, comments);
    }

    public Product reviewProduct (Product product, Rating rating, String comments) {
        List <Review> reviews = products.get(product);
        Collections.sort(reviews);
        products.remove (product, reviews);
        reviews.add (new Review(rating, comments));
        int sum = 0;
        for (Review review: reviews) {
            sum += review.rating().ordinal();
        }
        product = product.applyRating(Rateable.convert(
                Math.round ( (float)sum/reviews.size () )));
        products.put(product, reviews);
        return product;
    }
    public void printProductReport (int id) {
        printProductReport (findProduct (id) );
    }

    public void printProductReport (Product product){
        List <Review> reviews = products.get(product);
        StringBuilder txt = new StringBuilder ();
        txt.append (formatter. formatProduct (product));
        txt.append ('\n');
        for (Review review : reviews){
            txt.append(formatter.formatReview(review));
            txt.append ('\n');
        }
        if (reviews.isEmpty ()) {
            txt.append(formatter.getText ("no.reviews")) ;
            txt.append ('\n');
        }
        System.out.println (txt);
    }

    public void printProducts(Comparator<Product> sorter){
        List<Product> productList = new ArrayList<> (products.keySet ());
        productList.sort(sorter);
        StringBuilder txt = new StringBuilder ();

        for (Product product : productList) {
            txt.append (formatter.formatProduct (product) );
            txt.append ('\n');
        }
        System.out.println (txt);

    }












    public Product findProduct (int id){
        Product result = null;
        for(Product product : products.keySet()){
            if (product.getId() == id) {
                result = product;
                break;
            }
        }
        return result;
    }
    private ResourceFormatter formatter;

    private static Map<String, ResourceFormatter> formatters =
            Map.of ("en-GB", new ResourceFormatter (Locale.UK),
                    "en-US", new ResourceFormatter (Locale.US),
                    "ru-RU", new ResourceFormatter (new Locale ("ru", "RU")),
                    "fr-FR", new ResourceFormatter (Locale.FRANCE),
                    "zh-CN", new ResourceFormatter (Locale.CHINA));

    public void changeLocale (String languageTag) {
        formatter = formatters.getOrDefault (languageTag, formatters.get ("en-GB") );
    }

    public static Set<String> getSupportedLocales () {
        return formatters.keySet() ;
    }

    public ProductManager (String languageTag){
    changeLocale (languageTag) ;
    }








    private static class ResourceFormatter {



        private Locale locale;
        private ResourceBundle resources;
        private DateTimeFormatter dateFormat;
        private NumberFormat moneyFormat;

        private ResourceFormatter(Locale locale) {
            this.locale = locale;
            resources = ResourceBundle.getBundle ("labs.pm.data.resources", locale);
            dateFormat = DateTimeFormatter.ofLocalizedDate (FormatStyle.SHORT).localizedBy(locale);
            moneyFormat = NumberFormat.getCurrencyInstance (locale);
        }


        private String formatProduct(Product product) {
            String type = (product instanceof Food) ? resources.getString("food") : resources.getString("drink");
            return  MessageFormat. format (resources. getString ("product"), product. getName () ,
                    moneyFormat. format (product. getPrice () ), product.getRating ().getStars () ,
                    dateFormat. format (product. getBestBefore ()) , type);
        }

        private String formatReview(Review review){
            return MessageFormat.format (resources.getString ("review"),
                    review.rating ().getStars () , review.comments () );
        }

        private String getText(String key){
            return resources.getString(key);
        }

    }
}
