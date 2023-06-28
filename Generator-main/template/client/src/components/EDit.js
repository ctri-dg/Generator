import React, { useEffect, useState } from 'react'
import { Button, Form } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import array from './array';
import { Link } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
  
  
function Edit() {
  
    // Here usestate has been used in order
    // to set and get values from the jsx
    const [city, setcity] = useState('');
    const [area, setarea] = useState('');
    const [id, setid] = useState('');
    const [Manager, setManager] = useState('');
  
    // Used for navigation with logic in javascript
    let history = useNavigate()
  
    // Getting an index of an entry with an id
    var index = array.map(function (e) { 
        return e.id; }).indexOf(id);
  
    // Function for handling the edit and 
    // pushing changes of editing/updating
    const handleSubmit = (e) => {
  
        // Preventing from reload

        e.preventDefault();
      
  
        // Getting an index of an array
        let a = array[index] 
  
        // Putting the value from the input 
        // textfield and replacing it from 
        // existing for updation
        a.City= city
        a.Area = area
        a.Manager = Manager 
        a.Id = id
  
  //Redirecting to the main page here 
  history ('/')


// Useeffect take care that page will
// be rendered only once
useEffect(() => {
    setcity(localStorage.getItem('city'))
    setarea(localStorage.getItem('area'))
    setid(localStorage.getItem('id'))
    setManager(localStorage.getItem('Manager'))
  
}, [])

return (
    <div>
        <Form className="d-grid gap-2" 
            style={{ margin: '15rem' }}>

            {/* setting a name from the 
                input textfiled */}
            <Form.Group className="mb-3" 
                controlId="formBasicEmail">
                <Form.Control value={name}
                    onChange={e => setname(e.target.value)}
                    type="text" placeholder="Enter your Citye" />
            </Form.Group>

            
            <Form.Group className="mb-3" 
                controlId="formBasicPassword">
                <Form.Control value={age}
                    onChange={e => setage(e.target.value)}
                    type="text" placeholder="Area" />
            </Form.Group>

            {/* Handling an on click event 
                running an edit logic */}
                                <Button
                    onClick={e => handleSubmit(e) 
                    variant="primary" type="submit" size="lg">
                    update
                </Button>
 const handleNewBranch = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch(
        "http://localhost:8100/data-provider/v1/branch",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(branch),
        }
      );
      console.log(response);
      if (response.status == 200) {
        console.log("Object Edited successfully");
        showSuccess();
      } else {
        console.log("Object Editing failed");
        showFailure();
      }
    } catch (error) {
      console.error("Error occurred while editing object:", error);
      showFailure();
    }
  };
  
                {/* Redirecting to the main page after editing */}
                <Link className="d-grid gap-2" to='/'>
                    <Button variant="warning" 
                        size="lg">
                        Home
                    </Button>
                </Link>
            </Form>
        </div>
    )
}
  
export default Edit
