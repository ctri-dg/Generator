import 'bootstrap/dist/css/bootstrap.min.css';
import { useLocation } from 'react-router-dom';
import { useEffect, useState } from "react";
import axios from "axios";

import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { Spinner } from "react-bootstrap";
class Branch {
    id;
    city;
    area;
    numEmployees;
    manager;
    BranchDetailsClient()
    {
        this.id = null;
        this.city = '',
        this.area = '',
        this.numEmployees = 0,
        this.manager = ''
    }
    setCity(city)
    {
        this.city = city
    }
    setArea(area)
    {
        this.area = area;
    }
    setNumEmployes(num)
    {
        this.numEmployees = num;
    }
    setManager(manager)
    {
        this.manager = manager;
    }
  }

function Update()
{
    const [id,setId] = useState(0);
    const [getBranch,setGetBranch] = useState(false);
    const location = useLocation();
    const newdata = location.state;
    console.log(newdata);
    
    var [branch, setBranch] = useState(new Branch());
    useEffect(()=>{
        setBranch(newdata);
      }, [newdata]);
      
    

    const handleBranchChange = (event) => {
        const { name, value } = event.target;
        setBranch((prev) => {
           
          if (name === "BranchCity") {
            return {
                id : prev.id,
              city: value,
              area: prev.area,
              numEmployees: prev.numEmployees,
              manager: prev.manager,
            };
          } else if (name === "BranchArea") {
            return {
                id : prev.id,
              city: prev.city,
              area: value,
              numEmployees: prev.numEmployees,
              manager: prev.manager,
            };
          } else if (name === "BranchNumEmployees") {
            return {
                id : prev.id,
              city: prev.city,
              area: prev.area,
              numEmployees: Number(value),
              manager: prev.manager,
            };
          } else if (name === "BranchManager") {
            return {
                id : prev.id,
              city: prev.city,
              area: prev.area,
              numEmployees: prev.numEmployees,
              manager: value,
            };
          }
        });
      };
      const showSuccess = () =>
      toast.success(`Successfully updated branch.`);
      const showFailure = () =>
         toast.error(`Branch is not updated`);
    const handleUpdateBranch = async (e) => 
    {
        try{
            let config = {
                method: "put",
                url: `http://localhost:8100/data-provider/v1/branch`,
                headers: {
                  "Content-Type": "application/json",
                },
                data: branch,
              };
        axios.request(config).then(response =>
            {
                console.log(response);
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
    }
    console.log(branch);   
     return (
    <div className="d-flex justify-content-center">
      <div className="card cardview text-center">
        <div className="card-body">
          <h3> Update the product </h3>
          <br></br>
          <div className="input-group mb-3">
            <label className="input-group-text" id="inputGroup-sizing-default">
              Branch ID
            </label>
            <input
              className="form-control"
              type="number"
              name="BranchId"
              value={branch.id}
              readOnly
              required
              
            />
          </div>
          <div className="input-group mb-3">
            <label className="input-group-text" id="inputGroup-sizing-default">
              Branch City
            </label>
            <input
              className="form-control"
              type="text"
              name="BranchCity"
              value={branch.city}
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
              name="BranchArea"
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
              name="BranchNumEmployees"
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
              name="BranchManager"
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
            onClick={handleUpdateBranch}
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