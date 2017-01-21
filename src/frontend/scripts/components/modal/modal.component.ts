import {Component, Input, Directive, ViewChild, ElementRef, Output, EventEmitter} from "@angular/core";

@Component({
    selector: 'modal',
    templateUrl: 'modal.component.html',
    styleUrls: ['modal.component.css']
})
export class ModalComponent {
    @ViewChild("modalRoot")
    public modalRoot: ElementRef;

    @Input() title: string;
    @Input() closeOnEscape: boolean = true;
    @Input() closeOnOutsideClick: boolean = true;
    @Input() hideCloseButton: boolean = false;

    @Input() cancelButtonLabel: string;
    @Input() submitButtonLabel: string;
    @Input() submitButtonDisabled: boolean = false;
    @Input() dangerSubmitButtonLabel: string;

    @Output() public onSubmit: EventEmitter<any> = new EventEmitter();
    @Output() public onCancel: EventEmitter<any> = new EventEmitter();
    @Output() public onDangerSubmit: EventEmitter<any> = new EventEmitter();

    private isOpen: boolean = false;
    private backdropElement: HTMLDivElement;

    constructor() {
        this.createBackdrop();
    }

    private createBackdrop() {
        this.backdropElement = document.createElement("div");
        this.backdropElement.classList.add("modal-backdrop");
        this.backdropElement.classList.add("fade");
        this.backdropElement.classList.add("in");
    }

    public openModal() {
        if (this.isOpen)
            return;

        this.isOpen = true;
        document.body.appendChild(this.backdropElement);
        window.setTimeout(() => this.modalRoot.nativeElement.focus(), 0);
        document.body.classList.add("modal-open");
    }

    public closeModal() {
        if (!this.isOpen)
            return;

        this.isOpen = false;
        document.body.removeChild(this.backdropElement);
        document.body.classList.remove("modal-open");
    }

    protected cancel() {
        this.closeModal();
        this.onCancel.next();
    }

    public isModalOpen(): boolean {
        return this.isOpen;
    }

}

@Directive({
    selector: 'modal-body'
})
export class ModalComponentBody {
}

@Directive({
    selector: 'modal-footer'
})
export class ModalComponentFooter {
}