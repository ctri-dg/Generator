import './Header.scss'

const Header = ({title, description})=>{
    return <div className="Header d-flex flex-column">
        <div className="title">
            {title}
        </div>
        <div className="description">
            {description}
        </div>
    </div>
}

export default Header;