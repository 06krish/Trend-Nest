package com.trendnest.trendnest_backend.specifications;

import com.trendnest.trendnest_backend.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
// ProductSpecifications creates reusable database conditions,
// and JpaSpecificationExecutor executes the dynamically combined conditions.
// here we constructed query dynamically .
public class ProductSpecifications {
    // WHERE LOWER(name) LIKE '%shirt%'
    public static Specification<Product> hasName(String name){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%"+name.toLowerCase()+"%"));
    }
    // category_id = ?
    public static Specification<Product> hasCategory(Long categoryId){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("category").get("id"),categoryId);
    }
    // price >= minPrice
    public static Specification<Product> priceGreaterThanOrEqualTo(
            BigDecimal minPrice) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(
                        root.get("price"),
                        minPrice
                );
    }
    // price <= maxPrice
    public static Specification<Product> priceLessThanOrEqualTo(
            BigDecimal maxPrice) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(
                        root.get("price"),
                        maxPrice
                );
    }


}
