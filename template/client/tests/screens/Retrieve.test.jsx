import {fireEvent, render, screen} from '@testing-library/react'
import Retrieve from '../../src/screens/Retrieve/Retrieve'
import { MemoryRouter as Router } from "react-router-dom";

describe('Retrieve', ()=>{
    it("renders fetch all records option", ()=>{
        render(
            <Router><Retrieve/></Router>
        )
        const fetchAll = screen.getAllByText(/fetch all records/i)
        expect(fetchAll).toHaveLength(1)
    })

    it("renders search", ()=>{
        render(
            <Router><Retrieve/></Router>
        )
        const search = screen.getByText(/Search for records/i)
        expect(search).toBeInTheDocument()

        fireEvent.click(search)

        const searchbar = screen.getByRole('textbox')

        expect(searchbar).toBeInTheDocument();
    })

    it("renders search button", ()=>{
        render(
            <Router><Retrieve/></Router>
        )
        const btn = screen.getByRole("button")
        expect(btn).toBeEnabled();
    })
})