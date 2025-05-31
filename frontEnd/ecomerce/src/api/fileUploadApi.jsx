import axios from "axios";
import { API_BASE_URL } from "./constant";
import { getHeaders } from "./constant";

export const uploadImageApi = async (rawFile) => {
  const formData = new FormData();
  formData.append("file", rawFile);

  try {
    const response = await axios.post(
      `${API_BASE_URL}/api/upload`,
      formData,
      {
        headers: {
          ...getHeaders(), 
          "Content-Type": "multipart/form-data"
        }
      }
    );
    return response?.data?.url;
  } catch (err) {
    console.error("Error:", err);
    throw new Error("Upload failed");
  }
};