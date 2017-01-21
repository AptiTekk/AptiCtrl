/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiCtrl, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */
import {Component, Input, Output, EventEmitter, ViewChild} from "@angular/core";
import {ModalComponent} from "../modal/modal.component";

@Component({
    selector: 'deletion-confirmation-modal',
    templateUrl: 'deletion-confirmation-modal.component.html'
})
export class DeletionConfirmationModalComponent {

    @ViewChild('modal') modal: ModalComponent;

    @Input() message: string;
    @Input() warning: string;

    @Output() confirm: EventEmitter<void> = new EventEmitter<void>();

    public open() {
        this.modal.openModal();
    }

    onConfirm() {
        this.confirm.emit();
        this.modal.closeModal();
    }

}