import { createStore, applyMiddleware, compose } from "redux";
import thunk from "redux-thunk";
import rootReducer from "./reducers";

const initialState = {};
const middleware = [thunk];

let store;

const ReactReduxDevTools = window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__();

//  make it accessible to all browsers
// compose(...functions)： 从右到左来组合多个函数。
if (window.navigator.userAgent.includes("Chrome") && ReactReduxDevTools) {
    store = createStore(rootReducer, initialState, compose(applyMiddleware(...middleware),
        ReactReduxDevTools));
} else {
    store = createStore(rootReducer, initialState, compose(applyMiddleware(...middleware)));
}


// store = createStore(rootReducer, initialState, compose(applyMiddleware(...middleware),
//     window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()));
export default store;