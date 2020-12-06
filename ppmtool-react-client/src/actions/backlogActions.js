import axios from "axios";
import { GET_BACKLOG, GET_ERRORS, GET_PROJECT_TASK, DELETE_PROJECT_TASK } from "./types";

export const addProjectTask = (backlog_id, project_task, history) => async dispatch => {
    try {
        await axios.post(`/api/backlog/${backlog_id}`, project_task); // see backlog controller in backend
        history.push(`/projectBoard/${backlog_id}`);
        dispatch({ //clear errors if everything goes well
            type: GET_ERRORS,
            payload: {}
        });
    } catch (err) {
        dispatch({ //display errors if something goes wrong
            type: GET_ERRORS,
            payload: err.response.data
        });
    }

}

export const getBacklog = backlog_id => async dispatch => {
    try {
        const res = await axios.get(`/api/backlog/${backlog_id}`)
        dispatch({
            type: GET_BACKLOG,
            payload: res.data
        })
    } catch (err) {
        dispatch({
            type: GET_ERRORS,
            payload: err.response.data
        })

    }
}

export const getProjectTask = (backlog_id, projectTask_id, history) => async dispatch => {
    try {
        const res = await axios.get(`/api/backlog/${backlog_id}/${projectTask_id}`);
        dispatch({
            type: GET_PROJECT_TASK,
            payload: res.data
        });
    } catch (err) {
        history.push(`/dashboard`); // bad path: send people back to dashboard
    }
}

export const updateProjectTask = (backlog_id, projectTask_id, project_task, history) => async dispatch => {
    try {
        await axios.patch(`/api/backlog/${backlog_id}/${projectTask_id}`, project_task);
        history.push(`/projectBoard/${backlog_id}`);
        dispatch({
            type: GET_ERRORS,
            payload: {}
        });
    } catch (err) {
        dispatch({
            type: GET_ERRORS,
            payload: err.response.data
        })
    }
}

export const deleteProjectTask = (backlog_id, projectTask_id) => async dispatch => {
    if (window.confirm("Are you sure?")) {
        await axios.delete(`/api/backlog/${backlog_id}/${projectTask_id}`);
        dispatch({
            type: DELETE_PROJECT_TASK,
            payload: projectTask_id
        });
    }
};