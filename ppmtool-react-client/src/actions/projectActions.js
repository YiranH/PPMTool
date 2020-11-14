import axios from "axios";
import { GET_ERRORS } from "./types";

// if everything goes well, go to dashboard with updated project. If there is error, show what error you get
export const createProject = (project, history) => async function (dispatch) {
    try {
        const res = await axios.post("http://localhost:8080/api/project", project);
        history.push("/dashboard");
    } catch (err) {
        dispatch({
            type: GET_ERRORS,
            payload: err.response.data
        });
    }
}