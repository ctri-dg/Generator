import "./Retrieve.scss";
import Header from "../../components/Header/Header";
import { useState } from "react";
import ResultCard from "./ResultCard";
import axios from "axios";
import { Spinner } from "react-bootstrap";

const Retrieve = () => {
  const [mode, setMode] = useState("all");
  const [results, setResults] = useState([]);
  const [attr, setAttr] = useState("id");
  const [val, setVal] = useState("");
  const [waiting, setWaiting] = useState(false);

  const getAllRecords = () => {
    setWaiting(true);
    const response = axios
      .get("http://localhost:8001/data-provider/v1/resource")
      .then((response) => {
        setWaiting(false);
        // console.log(response.data);
        if (response.status == 200) {
          setResults(response.data);
        } else {
          console.log("Fetching object failed");
        }
      })
      .catch((error) => {
        setWaiting(false);
        console.error("Error occurred while posting object:", error);
      });
  };

  const getSomeRecords = () => {
    var config;
    if(attr === "id"){
      config = {
        method: "get",
        maxBodyLength: Infinity,
        url: `http://localhost:8001/data-provider/v1/resource/${val}`,
      };
      setWaiting(true);
      axios
        .request(config)
        .then((response) => {
          setResults([response.data]);
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(()=>{
          setWaiting(false);
        })
    }else{

      let data = JSON.stringify({
        [attr] : val,
      });
      config = {
        method: "post",
        maxBodyLength: Infinity,
        url: `http://localhost:8001/data-provider/v1/resource/${attr}`,
        headers: {
          "Content-Type": "application/json",
        },
        data: data,
      };
      setWaiting(true);
      axios
        .request(config)
        .then((response) => {
          setResults(response.data);
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(()=>{
          setWaiting(false);
        })
      }
  };

  return (
    <div className="Retrieve">
      <Header
        title="Retrieve"
        description="Read all records, or look for them based on certain attributes"
      />

      <div className="options d-flex flex-row justify-content-evenly">
        <div
          className={mode == "all" ? "option-active" : "option"}
          onClick={() => {
            setMode("all");
            setResults([]);
          }}
        >
          Fetch all records
        </div>
        <div
          className={mode == "search" ? "option-active" : "option"}
          onClick={() => {
            setMode("search");
            setResults([]);
          }}
        >
          Search for records
        </div>
        <button
          className="btn btn-dark"
          onClick={mode == "all" ? getAllRecords : getSomeRecords}
        >
          Search
        </button>
      </div>

      {mode == "search" ? (
        <div className="searchoptions d-flex flex-row justify-content-center align-items-center">
          <div className="dropdown">
            <button
              className="btn btn-dark dropdown-toggle p-2 m-4"
              type="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              {attr}
            </button>
            <ul className="dropdown-menu">
              <li><a className="dropdown-item" onClick={() => {setAttr("id");}}>id</a></li>
              <li><a className="dropdown-item" onClick={() => {setAttr("name");}}>name</a></li>
              <li><a className="dropdown-item" onClick={() => {setAttr("family");}}>family</a></li>
            </ul>
          </div>
          <div className="searchbar p-2 m-4">
            <input
              type="text"
              className="form-control shadow-none"
              onChange={(e) => {
                setVal(e.target.value);
              }}
              onKeyDown={(e) => {
                if (e.key == "Enter") {
                  getSomeRecords();
                }
              }}
            />
          </div>
        </div>
      ) : null}

      <div className="resultDisplay m-3">
        {waiting ? (
          <Spinner data-testid = "spinner"/>
        ) : results.length ? (
          <table className="table">
            <thead>
              <tr>
                <th scope="col">id</th>
                <th scope="col">name</th>
                <th scope="col">age</th>
                <th scope="col">family</th>
                <th scope="col">options</th>
              </tr>
            </thead>
            <tbody>
              {
              results.map((e) => {
                return (
                  <ResultCard
                    key={e.id}
                    id={e.id}
                    name={e.name}
                    age={e.age}
                    family={e.family}
                  />
                );
              })}
            </tbody>
          </table>
        ) : (
          <div className="noresults m-5">No records found</div>
        )}
      </div>
    </div>
  );
};

export default Retrieve;
