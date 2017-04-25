/**
 * Created by yanyj on 2017/4/25.
 */
import React from 'react';
import app from 'app/app.js';

import ReactDom from 'react-dom';

const App = app();

ReactDom.render(<App />, document.getElementById("app"));
