import * as Localization from 'expo-localization';
import { I18n } from '@aws-amplify/core';

const authScreenLabels = {
  en: {
    'Sign Up': 'Create new account',
    'Sign Up Account': 'Create a new account',
  },
};

// "en_US" -> "en", "es_CL" -> "es", etc
const languageLocale = Localization.locale;
I18n.setLanguage(languageLocale);
I18n.putVocabularies(authScreenLabels);

export default () => null;
