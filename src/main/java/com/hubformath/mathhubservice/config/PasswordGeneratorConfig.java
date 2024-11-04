package com.hubformath.mathhubservice.config;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PasswordGeneratorConfig {

    public static String generatePassword() {
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        CharacterRule lowerCaseRule = new CharacterRule(EnglishCharacterData.LowerCase);
        lowerCaseRule.setNumberOfCharacters(4);

        CharacterRule upperCaseRule = new CharacterRule(EnglishCharacterData.UpperCase);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterRule digitRule = new CharacterRule(EnglishCharacterData.Digit);
        digitRule.setNumberOfCharacters(2);

        CharacterRule splCharRule = new CharacterRule(EnglishCharacterData.SpecialAscii);
        splCharRule.setNumberOfCharacters(2);

        return passwordGenerator.generatePassword(10, List.of(lowerCaseRule, upperCaseRule, digitRule, splCharRule));
    }

}
