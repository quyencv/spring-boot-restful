package com.tutorial.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@XmlRootElement(name = "books")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BookDTO extends BaseDTO {

    private static final long serialVersionUID = -2636280703576239264L;

    @Column(name = "title", nullable = false, length = 100)
    @NotEmpty
    @Size(max = 100)
    private String title;

    @NotEmpty
    @Size(max = 1000)
    private String description;

    @NotEmpty
    @Size(max = 100)
    private String author;

    @NotNull
    private Double price;

}
