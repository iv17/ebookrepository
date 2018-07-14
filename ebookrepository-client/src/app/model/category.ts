export class Category {
    id: number;
    name: String;

    constructor(inter: CategoryInterface = {}) {
        this.id = inter.id;
        this.name = inter.name;
    }
}
interface CategoryInterface {
    id?: number;
    name?: String;
}