import {render, screen} from '@testing-library/react'
import Navbar from '../../src/components/Navbar/Navbar';

describe('Navbar', () => {
    it('renders retrieve option', () => {
        render(<Navbar/>);
        const retrieveOption = screen.getByText(/retrieve/i);
        expect(retrieveOption).toBeInTheDocument();
    });
    it('renders create option', () => {
        render(<Navbar/>);
        const createOption = screen.getByText(/create/i);
        expect(createOption).toBeInTheDocument();
    });
});
