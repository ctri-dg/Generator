import "./Delete.scss";
import Header from "../../components/Header/Header";
import { useEffect, useState } from "react";
import axios from "axios";

import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { Spinner } from "react-bootstrap";
import { useLocation } from "react-router-dom";

const Delete = () => {
  const [id, setId] = useState("");
  const [waiting, setWaiting] = useState(false);

  const showSuccess = () =>
    toast.success(`Successfully deleted id = ${id}.`);
  const showFailure = () =>
    toast.error("Couldn't execute the delete operation");

  const deleteById = () => {
    setWaiting(true);
    axios
      .delete(`http://localhost:8100/data-provider/v1/branch/${id}`)
      .then((response) => {
        if (response.status == 200) {
          showSuccess();
        } else {
          showFailure();
        }
        setWaiting(false);
      })
      .catch((e) => {
        showFailure();
        setWaiting(false);
      });
  };

  const location = useLocation();
  const data = location.state;

  useEffect(()=>{
    setId(data);
  }, [data]);

  return (
    <div className="Delete">
      <Header
        title="Delete"
        description="Use retrieve to fetch the id which you wish to delete and enter it here"
      />
      <div className="deleteinp d-flex flex-row justify-content-center">
        <input
          type="text"
          className="form-control shadow-none"
          placeholder="id"
          onChange={(e) => {
            setId(e.target.value);
          }}
          defaultValue={data}
        />
        {waiting ? (
          <Spinner />
        ) : (
          <button className="btn btn-dark" onClick={deleteById}>
            Delete
          </button>
        )}
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
};

export default Delete;
