import { createStore, applyMiddleware, compose } from "redux";
import thunk from "redux-thunk";
import rootReducer from "./reducers";

const initialState = {};
const middleware = [thunk];

let store;

//  make it accessible to all browsers
// compose(...functions)： 从右到左来组合多个函数。
if (window.navigator.userAgent.includes("Chrome")) {
    store = createStore(rootReducer, initialState, compose(applyMiddleware(...middleware),
        window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()));
} else {
    store = createStore(rootReducer, initialState, compose(applyMiddleware(...middleware)));
}


// store = createStore(rootReducer, initialState, compose(applyMiddleware(...middleware),
//     window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()));
export default store;