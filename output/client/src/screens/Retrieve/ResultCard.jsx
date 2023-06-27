import { useState } from "react";
import "./ResultCard.scss";
import { AiFillDelete, AiFillEdit } from "react-icons/ai";
import { useNavigate } from "react-router-dom";

const ResultCard = ({ id, city, area, numEmployees, manager }) => {
  const [show, setShow] = useState(false);
  const navigate = useNavigate();

  return (
    <div className="ResultCardTotal d-flex flex-column ">
      <div className="ResultCard d-flex flex-row flex-wrap"
        onClick={()=>{
            setShow(!show)
        }}
      >
        <div className="id">{id}</div>
        <div className="areancity">{`${area}, ${city}`}</div>
        <div className="numEmployees">{`${numEmployees} employees`}</div>
        <div className="manager">{`Managed by ${manager}`}</div>
      </div>
      {show ? (
        <div className="options d-flex justify-content-end">
          <AiFillEdit className="icons" size={25}
            onClick={()=>{
                navigate(
                    "/update",
                    {
                        state : {
                            "id" : id, 
                            "city" : city, 
                            "area" : area,
                            "manager" : manager,
                            "numEmployees" : numEmployees
                        }
                    }
                )
            }}
          />
          <AiFillDelete className="icons" size={25}
             onClick={()=>{
                navigate("/delete", {state : id})
            }}
          />
        </div>
      ) : null}
    </div>
  );
};

export default ResultCard;
