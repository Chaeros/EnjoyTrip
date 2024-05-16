export function setLocalStorage(key, value) {
  if (typeof window !== "undefined") {
    localStorage.setItem(key, value);
  }
}

export function getLocalStorage(key) {
  if (typeof window !== "undefined") {
    return localStorage.getItem(key);
  }
  return null;
}

export function removeLocalStorage(key) {
  if (typeof window !== "undefined") {
    localStorage.removeItem(key);
  }
}
