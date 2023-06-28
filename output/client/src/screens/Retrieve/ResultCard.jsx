import { useState } from "react";
import "./ResultCard.scss";
import { AiFillDelete, AiFillEdit } from "react-icons/ai";
import { useNavigate } from "react-router-dom";

const ResultCard = ({
	id, city, area, manager, employees, revenue, 
}) => {

  const navigate = useNavigate();
  return (
    <tr
        className="ResultCard"
      >
        <th scope="row">{id}</th>
        <td>{city}</td>
        <td>{area}</td>
        <td>{manager}</td>
        <td>{employees}</td>
        <td>{revenue}</td>

        <td className="options">
          <AiFillEdit
            className="icons"
            size={25}
            onClick={() => {
              navigate("/update", {
                state: {
				id : id,
				city : city,
				area : area,
				manager : manager,
				employees : employees,
				revenue : revenue,
                },
              });
            }}
          />
          <AiFillDelete
            className="icons"
            size={25}
            onClick={() => {
              navigate("/delete", { 
                state: id 
              });
            }}
          />
        </td>
    </tr>
  );
};

export default ResultCard;
