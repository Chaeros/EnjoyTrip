const isPasswordStrengthStrong = (password) => {
  const hasSpecialChar = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/.test(
    password
  );
  const hasRegularChar = /[a-zA-Z0-9]+/.test(password);

  // 특수문자와 일반 문자 각각 1자리 이상인지 확인
  return hasSpecialChar && hasRegularChar;
};

const isPasswordLengthCorrect = (password) => {
  if (password.length >= 8 && password.length <= 32) {
    return true;
  }
  return false;
};

export { isPasswordStrengthStrong, isPasswordLengthCorrect };
