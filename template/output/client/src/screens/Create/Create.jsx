/* eslint-disable no-unused-vars */
import "bootstrap/dist/css/bootstrap.min.css";
import { useState } from "react";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { Spinner } from "react-bootstrap";
import {EntityDetailsClient} from "../Model/ModelDetailsClient" 
function Create() {
  var [entity, setEntity] = useState(new EntityDetailsClient());
  const showSuccess = () =>
    toast.success(`Successfully created.`);
  const showFailure = () =>
    toast.error("Couldn't execute the create operation");
  const handleEntityChange = (event) => {
    const { name, value } = event.target;
    setEntity((prev) => (
      {
        ...prev,
        [name] : value
      }
    ));
  };

  const handleNewEntity = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch(
        "http://localhost:8100/data-provider/v1/branch",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(entity),
        }
      );
      console.log(response);
      if (response.status == 200) {
        console.log("Object posted successfully");
        showSuccess();
      } else {
        console.log("Object posting failed");
        showFailure();
      }
    } catch (error) {
      console.error("Error occurred while posting object:", error);
      showFailure();
    }
  };

  return (
    <div className="d-flex justify-content-center  ">
      <div className="card cardview text-center">
        <div className="card-body">
          <h3> Add a new Branch </h3>
        <div className="input-group mb-3">
            <label className="input-group-text" id="inputGroup-sizing-default">
              Branch city
            </label>
            <input
              className="form-control"
              type="text"
              name="city"
              value={model.city}
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
              value={model.area}
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
              value={model.manager}
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
              value={model.employees}
              placeholder="Branch employees"
              
              onChange={handleEntityChange}
              required
              
            />
            <br/>
        </div>
        <div className="input-group mb-3">
            <label className="input-group-text" id="inputGroup-sizing-default">
              Branch revenue
            </label>
            <input
              className="form-control"
              type="null"
              name="revenue"
              value={model.revenue}
              placeholder="Branch revenue"
              
              onChange={handleEntityChange}
              required
              
            />
            <br/>
        </div>
          <button
            className="btn btn-primary btn-dark"
            type="submit"
            onClick={handleNewEntity}
          >
            {" "}
             Submit {" "}
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
export default Create;
