package com.backbone.core.demo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class Review extends RepresentationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // disables HIBERNATE_SEQUENCE
    private int id;

    private String userName;
    private int productId;

    private String title;
    private int rating;
    private boolean isVerifiedPurchase;
    private boolean isHelpful;
    private boolean isAbuse;
//    private List<String> tags;
    private String description;
}
