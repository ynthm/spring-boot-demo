package com.ynthm.validator.demo.entity;

import com.ynthm.validator.demo.common.validator.PhoneNumber;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author ethan
 */
@Data
public class Account {

  @NotNull @Valid private PhoneNumber phoneNumber;
}
