package nhonnguyen.food.Validator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import nhonnguyen.food.Validator.annotation.ValidUserId;
import nhonnguyen.food.entity.User;

public class ValidUserIdValidator implements ConstraintValidator<ValidUserId, User> {

    @Override
    public  boolean isValid(User user, ConstraintValidatorContext context){
        if(user==null)
            return true;
        return  user.getId() !=null;
    }
}