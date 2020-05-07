package com.backbone.core.demo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class Review extends RepresentationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int userName;
    private int productId;

    private String title;
    private int rating;
    private boolean isVerifiedPurchase;
    private boolean isHelpful;
    private boolean isAbuse;
//    private List<String> tags;
    private String description;
}
