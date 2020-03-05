import React, { useEffect } from 'react';
import axios from "axios";
import './App.css';
import { useState } from 'react';

function App() {

  const [text, setText] = useState("")

  useEffect(() => {
    axios.get('http://127.0.0.1:8080/').then((res) => {
      setText(res.data)
    })
  })

  return (
    <div className="App">
      {text}
    </div>
  );
}

export default App;
