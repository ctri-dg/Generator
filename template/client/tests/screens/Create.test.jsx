import {render, screen} from '@testing-library/react'
import fields from '../formFields.json'
import Create from '../../src/screens/Create/Create'


describe('Create', ()=>{
    it("renders all fields", ()=>{
        render(<Create/>)
        fields.forEach((field)=>{
            const el = screen.getAllByText(new RegExp(field.label, "i"))
            expect(el.length).toBeGreaterThan(0)
        })
    })

    it("has create button", ()=>{
        render(<Create/>)
        const btn = screen.getByRole("button")
        expect(btn).toBeEnabled()
    })
})