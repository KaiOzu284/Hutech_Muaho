package nhonnguyen.food.Validator;

import jakarta.validation.ConstraintValidatorContext;
import nhonnguyen.food.Validator.annotation.ValidCategoryId;
import nhonnguyen.food.entity.Category;
import jakarta.validation.ConstraintValidator;


public class ValidCategoryIdValidator implements ConstraintValidator<ValidCategoryId, Category> {

    @Override
    public  boolean isValid(Category category, ConstraintValidatorContext context){
        return category != null && category.getId() != null;
    }
}