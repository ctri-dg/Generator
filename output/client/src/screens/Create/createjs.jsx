/* eslint-disable no-unused-vars */
import "bootstrap/dist/css/bootstrap.min.css";
import { useState } from "react";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { Spinner } from "react-bootstrap";
import React from 'react';

class BranchDetailsClient {
  city
  area
  numEmployees
  manager

  constructor() {
    this.city = "";
    this.area = "";
    this.numEmployees = 0;
    this.manager = "";
  }
}

function Create() {
  const [branch, setBranch] = useState<BranchDetailsClient>(new BranchDetailsClient());
  const showSuccess = () => toast.success("Successfully created branch.");
  const showFailure = () => toast.error("Couldn't execute the create operation");
  const handleBranchChange = (event) => {
    const { name, value } = event.target;
    setBranch((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleNewBranch = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch("http://localhost:8100/data-provider/v1/branch", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(branch),
      });
      console.log(response);
      if (response.status === 200) {
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
          <h3> Add a new product </h3>
          <br></br>
          <div className="input-group mb-3">
            <label className="input-group-text" id="inputGroup-sizing-default">
              Branch City
            </label>
            <input
              className="form-control"
              type="text"
              name="city"
              value={branch.city}
              placeholder="Branch city"
              onChange={handleBranchChange}
              required
            />
          </div>

          <div className="input-group mb-3">
            <label className="input-group-text" id="inputGroup-sizing-default">
              Branch Area
            </label>
            <input
              className="form-control"
              type="text"
              name="area"
              value={branch.area}
              placeholder="Branch Area"
              onChange={handleBranchChange}
              required
            />
          </div>

          <div className="input-group mb-3">
            <label className="input-group-text" id="inputGroup-sizing-default">
              Employees
            </label>
            <input
              className="form-control"
              type="number"
              name="numEmployees"
              value={branch.numEmployees}
              placeholder="0"
              onChange={handleBranchChange}
              required
            />
          </div>
          <div className="input-group mb-3">
            <label className="input-group-text" id="inputGroup-sizing-default">
              {" "}
              Branch Manager
            </label>
            <input
              className="form-control"
              type="text"
              name="manager"
              value={branch.manager}
              placeholder="Mr.Rana"
              onChange={handleBranchChange}
              required
            />
          </div>
          <br />
          <button
            className="btn btn-primary btn-dark"
            type="submit"
            onClick={handleNewBranch}
          >
            {" "}
            Add new Branch{" "}
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
