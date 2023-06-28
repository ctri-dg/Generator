import { useState } from 'react'
import { BrowserRouter as Router, Routes, Route}from 'react-router-dom'

import Create from './screens/Create/create';
import Retrieve from './screens/Retrieve/Retrieve'
import Navbar from './components/Navbar/Navbar'
import Delete from './screens/Delete/Delete';
import Update from './screens/Update/Update';


function App() {

  return (
    <div className="App">
      <Router>
        <Navbar/>
        <Routes>
          <Route path="/create" element={<Create/>} />
          <Route path='/' element={<Retrieve/>}/>
          <Route path='/retrieve' element={<Retrieve/>}/>
          <Route path='/delete' element={<Delete/>}/>
          <Route path = '/update' element = {<Update/>}/>
        </Routes>
      </Router>
    </div>
  )
}

export default App;
