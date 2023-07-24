import {fireEvent, getAllByRole, render, screen, waitFor, waitForElementToBeRemoved} from '@testing-library/react'
import fields from '../formFields.json'
import Create from '../../src/screens/Create/Create'
import Retrieve from '../../src/screens/Retrieve/Retrieve'
import Delete from '../../src/screens/Delete/Delete'
import { MemoryRouter as Router } from "react-router-dom";

describe('Complete flow test', ()=>{
    var id = null

    const values = fields.map((field)=>{
        if(field.type == "string"){
            return field.label + "testVal"
        }else if(field.type == "int"){
            return 40
        }
    })

    it("creates a record", async ()=>{
        render(<Create/>)
        const inputFields = screen.getAllByRole("textbox")
        inputFields.forEach((e, i)=>{
            fireEvent.change(e, {target : {value : values[i]}})
        })
        const btn = screen.getByRole("button")
        fireEvent.click(btn)
        const spinner = screen.getByTestId("spinner")
        await waitFor(()=>{
            expect(spinner).not.toBeInTheDocument()
        })

    })

    it("retrieves the record", async ()=>{
        render(<Router><Retrieve/></Router>)
        const fetchAll = screen.getByText(/fetch all records/i)
        fireEvent.click(fetchAll)
        const searchBtn = screen.getByRole("button")
        fireEvent.click(searchBtn)
        await waitFor(()=>{
            const rows = screen.getAllByRole("row")
            var insertedRow = null;
            rows.forEach((row)=>{
                var n = fields.length
                var found = true
                row.childNodes.forEach((node, i)=>{
                    if(i>=1 && i<=n){
                        if(node.textContent != values[i-1]){
                            found = false;
                        }
                    }
                })
                if(found){
                    insertedRow = row;
                    id = row.childNodes.item(0).textContent
                }
            })
            expect(insertedRow).not.toBeNull()
        })
    })

    it("deletes the record", async ()=>{
        render(<Router><Delete/></Router>)
        const input = screen.getByRole("textbox")
        fireEvent.change(input, {target : {value : id}})
        const btn = screen.getByRole("button")
        fireEvent.click(btn)
        const spinner = screen.getByTestId("spinner")
        await waitFor(()=>{
            expect(spinner).not.toBeInTheDocument()
        })
    })
})