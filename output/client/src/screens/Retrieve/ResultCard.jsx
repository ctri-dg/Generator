import { useState } from "react";
import "./ResultCard.scss";
import { AiFillDelete, AiFillEdit } from "react-icons/ai";
import { useNavigate } from "react-router-dom";

const ResultCard = ({
	id, name, age, family, 
}) => {

  const navigate = useNavigate();
  return (
    <tr
        className="ResultCard"
      >
        <th scope="row">{id}</th>
        <td>{name}</td>
        <td>{age}</td>
        <td>{family}</td>

        <td className="options">
          <AiFillEdit
            className="icons"
            size={25}
            onClick={() => {
              navigate("/update", {
                state: {
				id : id,
				name : name,
				age : age,
				family : family,
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
