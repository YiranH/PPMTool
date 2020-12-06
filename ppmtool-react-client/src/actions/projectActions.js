// talk to the backend
import axios from "axios";
import { GET_ERRORS, GET_PROJECTS, GET_PROJECT, DELETE_PROJECT } from "./types";

// we pass a project object, if everything goes well, go to dashboard with updated project. If there is error, show what error you get
export const createProject = (project, history) => async function (dispatch) {
    try {
        // server response
        await axios.post("/api/project", project);
        // sent view to dashboard
        history.push("/dashboard");
        dispatch({
            type: GET_ERRORS,
            payload: {}
        });
    } catch (err) {
        dispatch({
            type: GET_ERRORS,
            payload: err.response.data
        });
    }
};

export const getProjects = () => async dispatch => {
    const res = await axios.get("/api/project/all");
    dispatch({
        type: GET_PROJECTS,
        payload: res.data
    });
};

// wire 
export const getProject = (id, history) => async dispatch => {
    try {
        const res = await axios.get(`/api/project/${id}`);
        dispatch({
            type: GET_PROJECT,
            payload: res.data
        });
    } catch (err) {
        history.push("/dashboard"); // bad path: send people back to dashboard
    }

};

export const deleteProject = id => async dispatch => {
    if (window.confirm("Are you sure?")) {
        await axios.delete(`/api/project/${id}`);
        dispatch({
            type: DELETE_PROJECT,
            payload: id
        });
    }
};