//import begin
import { useState } from "react";
import "./ResultCard.scss";
import { AiFillDelete, AiFillEdit } from "react-icons/ai";
import { useNavigate } from "react-router-dom";
//import end

//funcdec begin
const ResultCard = ({
  %s
}) => {
//funcdec end
  const navigate = useNavigate();
  return (
    <tr
        className="ResultCard"
      >
        <th scope="row">{id}</th>
        <td>{area}</td>
        <td>{city}</td>
        <td>{numEmployees}</td>
        <td>{manager}</td>
        <td className="options">
          <AiFillEdit
            className="icons"
            size={25}
            onClick={() => {
              navigate("/update", {
                state: {
                  id: id,
                  city: city,
                  area: area,
                  manager: manager,
                  numEmployees: numEmployees,
                },
              });
            }}
          />
          <AiFillDelete
            className="icons"
            size={25}
            onClick={() => {
              navigate("/delete", { state: id });
            }}
          />
        </td>
    </tr>
  );
};

export default ResultCard;
