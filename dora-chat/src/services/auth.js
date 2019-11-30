import { API_URL } from "../config/constant.js";

export const signin = async (credential) => {
  const authUrl = `${API_URL}/auth`
  const res = await fetch(authUrl, {
    method: "POST",
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(credential)
  });
  return res.json()
}