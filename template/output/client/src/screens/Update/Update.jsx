import 'bootstrap/dist/css/bootstrap.min.css';
import { useLocation } from 'react-router-dom';
import { useEffect, useState } from "react";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { Spinner } from "react-bootstrap";
import {Default as EntityDetailsClient } from '../Model/ModelDetailsClient';
function Update()
{
    const location = useLocation();
    const newdata = location.state;
    var [entity, setEntity] = useState(new EntityDetailsClient());
    useEffect(()=>{
        setEntity(newdata);
      }, [newdata]);
    const handleEntityChange = (event) => {
        const { name, value } = event.target;
        setBranch((prev) => (
          {
            ...prev,
            [name] : value
          }
        ));
      };
      const showSuccess = () =>
          toast.success(`Successfully updated .`);
      const showFailure = () =>
         toast.error(`Error occured update failed`);
    const handleSubmit = async (e) => 
    {
        try{
            let config = {
                method: "put",
                url: `http://localhost:8100/data-provider/v1/branch`,
                headers: {
                  "Content-Type": "application/json",
                },
                data: entity,
              };
        axios.request(config).then(response =>
            {
                if(response.status == 200)
                {
                    showSuccess();
                }
                else
                {
                    showFailure();
                }
            })
    }
    catch(err) {
        console.log(err)
    }
    };   
     return (
    <div className="d-flex justify-content-center">
      <div className="card cardview text-center">
        <div className="card-body">
          <h3> Update the Branch </h3>
        <div className="input-group mb-3">
            <label className="input-group-text" id="inputGroup-sizing-default">
              Branch id
            </label>
            <input
              className="form-control"
              type="number"
              name="id"
              value={entity.id}
              placeholder="Branch id"
              readOnly
              onChange={handleEntityChange}
              required
              
            />
            <br/>
        </div>
        <div className="input-group mb-3">
            <label className="input-group-text" id="inputGroup-sizing-default">
              Branch city
            </label>
            <input
              className="form-control"
              type="text"
              name="city"
              value={entity.city}
              placeholder="Branch city"
              
              onChange={handleEntityChange}
              required
              
            />
            <br/>
        </div>
        <div className="input-group mb-3">
            <label className="input-group-text" id="inputGroup-sizing-default">
              Branch area
            </label>
            <input
              className="form-control"
              type="text"
              name="area"
              value={entity.area}
              placeholder="Branch area"
              
              onChange={handleEntityChange}
              required
              
            />
            <br/>
        </div>
        <div className="input-group mb-3">
            <label className="input-group-text" id="inputGroup-sizing-default">
              Branch manager
            </label>
            <input
              className="form-control"
              type="text"
              name="manager"
              value={entity.manager}
              placeholder="Branch manager"
              
              onChange={handleEntityChange}
              required
              
            />
            <br/>
        </div>
        <div className="input-group mb-3">
            <label className="input-group-text" id="inputGroup-sizing-default">
              Branch employees
            </label>
            <input
              className="form-control"
              type="number"
              name="employees"
              value={entity.employees}
              placeholder="Branch employees"
              
              onChange={handleEntityChange}
              required
              
            />
            <br/>
        </div>
          <button
            className="btn btn-primary btn-dark"
            type="submit"
            onClick={handleSubmit}
          >
            {" "}
                Update Branch{" "}
          </button>
        </div>
      </div>
      <ToastContainer
        position="bottom-right"
        autoClose={5000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme="dark"
      />
    </div>
    );
}
export default Update;
