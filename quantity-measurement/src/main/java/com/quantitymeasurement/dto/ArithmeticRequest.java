package com.quantitymeasurement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * UC15: DTO for binary arithmetic operations (add, subtract, divide) on two quantities.
 * UC7: targetUnit is optional — defaults to the unit of the first operand.
 */
public class ArithmeticRequest {

    @NotNull  private Double  value1;
    @NotBlank private String  unit1;
    @NotNull  private Double  value2;
    @NotBlank private String  unit2;
    @NotBlank private String  category;
              private String  targetUnit; // UC7: optional target unit

    public ArithmeticRequest() {}

    public Double getValue1()     { return value1; }
    public String getUnit1()      { return unit1; }
    public Double getValue2()     { return value2; }
    public String getUnit2()      { return unit2; }
    public String getCategory()   { return category; }
    public String getTargetUnit() { return targetUnit; }

    public void setValue1(Double v)      { this.value1 = v; }
    public void setUnit1(String u)       { this.unit1 = u; }
    public void setValue2(Double v)      { this.value2 = v; }
    public void setUnit2(String u)       { this.unit2 = u; }
    public void setCategory(String c)    { this.category = c; }
    public void setTargetUnit(String t)  { this.targetUnit = t; }
}
