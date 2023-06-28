import { useState } from "react";
import "./ResultCard.scss";
import { AiFillDelete, AiFillEdit } from "react-icons/ai";
import { useNavigate } from "react-router-dom";

const ResultCard = ({
//break
}) => {

  const navigate = useNavigate();
  return (
    <tr
        className="ResultCard"
      >
//break
        <th scope="row">{%s}</th>
        <td>{%s}</td>

        <td className="options">
          <AiFillEdit
            className="icons"
            size={25}
            onClick={() => {
              navigate("/update", {
                state: {
//break
                },
              });
            }}
          />
          <AiFillDelete
            className="icons"
            size={25}
            onClick={() => {
              navigate("/delete", { 
//break
                state: %s 
              });
            }}
          />
        </td>
    </tr>
  );
};

export default ResultCard;
//break
