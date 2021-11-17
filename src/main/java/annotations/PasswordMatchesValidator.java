package annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.sbproject.schedule.models.UserDTO;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
  
  @Override
  public void initialize(PasswordMatches constraintAnnotation) {}
  
  @Override
  public boolean isValid(Object obj, ConstraintValidatorContext context){
      UserDTO user = (UserDTO) obj;
      if(user.getMatchingPassword() == null)
    	  return false;
      return user.getPassword().equals(user.getMatchingPassword());
  }
}
