import axios from "axios";

const setJWTToken = token => {
    if (token) {
        // set token in header
        axios.defaults.headers.common["Authorization"] = token;
    } else {
        delete axios.defaults.headers.common["Authorization"];
    }
};

export default setJWTToken;