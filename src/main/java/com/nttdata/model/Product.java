package com.nttdata.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**Se crea la clase Product, donde se declaran las variables.*/
@Document(collection = "product")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
  @Id
  private String id;
  @Field(name = "productName")
  private String productName;
  
  @Field(name = "productType")
  private String productType;
  
  @Field(name = "condition")
  private Condition condition;

}
